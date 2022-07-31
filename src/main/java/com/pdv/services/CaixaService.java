package com.pdv.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.pdv.dto.CaixaDTO;
import com.pdv.dto.PagamentoDTO;
import com.pdv.entities.Caixa;
import com.pdv.entities.Pagamento;
import com.pdv.repositories.CaixaRepository;
import com.pdv.repositories.PagamentoRepository;

@Service
public class CaixaService {
	@Autowired
	private CaixaRepository caixaRepository;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	public List<?> findAll() {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		List<Caixa> listCaixa = caixaRepository.findAll();
		List<CaixaDTO> listDTO = new ArrayList<CaixaDTO>();

		listCaixa.forEach(a -> {
			CaixaDTO dto = new CaixaDTO();
			dto.setId(a.getId());
			dto.setAberto(a.isAberto());
			dto.setNome(a.getNome());
			dto.setValorAbertura(a.getValorAbertura());
			dto.setValorFechamento(a.getValorFechamento());
			dto.setDataAbertura(a.getDataAbertura().format(formatter));
			dto.setDataFechamento(a.getDataFechamento() != null ? a.getDataFechamento().format(formatter) : null);
			listDTO.add(dto);
		});
		List<CaixaDTO> listOrder= listDTO.stream().sorted(Comparator.comparingLong(CaixaDTO::getId).reversed()).collect(Collectors.toList());
		return listOrder;
	}

	public Caixa findById(Long id) {
		Optional<Caixa> objCategoria = caixaRepository.findById(id);
		return objCategoria.get();
	}

	public Caixa insert(Caixa caixa) {

		if (verificaAbertoUsuario()) {
			caixa.setDataAbertura(LocalDateTime.now());
			caixa.setIdUsuario(getIdUserLogado());
			caixa.setNome(getNomeUserLogado());
			caixa.setAberto(true);
		} else {
			return null;
		}

		return caixaRepository.save(caixa);
	}

	private Long getIdUserLogado() {
		UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return principal.getId();
	}

	private String getNomeUserLogado() {
		UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return principal.getUsername();
	}

	private boolean verificaAbertoUsuario() {
		Caixa caixas = caixaRepository.findByIdUsuarioAndAberto(getIdUserLogado(), true);
		return caixas != null ? false : true;
	}

	private LocalDateTime getInicio() {
		LocalDateTime localDateTime5 = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(),
				LocalDateTime.now().getDayOfMonth(), 00, 00, 00);
		return localDateTime5;
	}

	public Caixa update(Long id, Caixa caixa) {
		Optional<Caixa> newCaixa = caixaRepository.findById(id);

		newCaixa.get().setNome(caixa.getNome());
		newCaixa.get().setIdUsuario(caixa.getIdUsuario());
		newCaixa.get().setValorFechamento(caixa.getValorFechamento());
		//
		newCaixa.get().setValorFechamentoDinheiro(caixa.getValorFechamentoDinheiro());
		newCaixa.get().setValorFechamentoPix(caixa.getValorFechamentoPix());
		newCaixa.get().setValorFechamentoCartaoCredito(caixa.getValorFechamentoCartaoCredito());
		newCaixa.get().setValorFechamentoCartaoDebito(caixa.getValorFechamentoCartaoDebito());
		newCaixa.get().setValorFechamentoConsignado(caixa.getValorFechamentoConsignado());
		//
		newCaixa.get().setDiferencaDinheiro(caixa.getDiferencaDinheiro());
		newCaixa.get().setDiferencaPix(caixa.getDiferencaPix());
		newCaixa.get().setDiferencaCartaoCredito(caixa.getDiferencaCartaoCredito());
		newCaixa.get().setDiferencaCartaoDebito(caixa.getDiferencaCartaoDebito());

		newCaixa.get().setDiferencaConsignado(caixa.getDiferencaConsignado());
		//
		newCaixa.get().setValorPagamentoDinheiro(caixa.getValorPagamentoDinheiro());
		newCaixa.get().setValorPagamentoPix(caixa.getValorPagamentoPix());
		newCaixa.get().setValorPagamentoCartaoCredito(caixa.getValorPagamentoCartaoCredito());
		newCaixa.get().setValorPagamentoCartaoDebito(caixa.getValorPagamentoCartaoDebito());
		newCaixa.get().setValorPagamentoConsignado(caixa.getValorPagamentoConsignado());

		newCaixa.get().setAberto(false);
		newCaixa.get().setDataFechamento(LocalDateTime.now());

		return caixaRepository.save(newCaixa.get());
	}

	public Long getIdUltimoAbertoNow() {
		List<Caixa> caixas = caixaRepository.findByAbertoAndDataAberturaBetween(true, getInicio(),
				getInicio().plusHours(23).plusMinutes(59));
		return caixas.get(0).getId();
	}

	public Caixa getFechamentoCaixa() {
		Caixa caixa = caixaRepository.findByIdUsuarioAndAberto(getIdUserLogado(), true);

		if (caixa != null) {
			double valorPagamentoDinheiro = caixa.getPagamentos().stream()
					.filter(c -> c.getModoPagamento().getId() == 1).mapToDouble(f -> f.getValorPagamento()).sum();

			caixa.setValorPagamentoDinheiro(valorPagamentoDinheiro);
			caixa.setValorPagamentoPix(caixa.getPagamentos().stream().filter(c -> c.getModoPagamento().getId() == 2)
					.mapToDouble(f -> f.getValorPagamento()).sum());
			caixa.setValorPagamentoCartaoCredito(caixa.getPagamentos().stream()
					.filter(c -> c.getModoPagamento().getId() == 3).mapToDouble(f -> f.getValorPagamento()).sum());
			caixa.setValorPagamentoCartaoDebito(caixa.getPagamentos().stream()
					.filter(c -> c.getModoPagamento().getId() == 4).mapToDouble(f -> f.getValorPagamento()).sum());
			caixa.setValorPagamentoConsignado(caixa.getPagamentos().stream()
					.filter(c -> c.getModoPagamento().getId() == 5).mapToDouble(f -> f.getValorPagamento()).sum());

			caixa.setValorFechamentoDinheiro(
					caixa.getValorFechamentoDinheiro() == null ? 0 : caixa.getValorFechamentoDinheiro());
			caixa.setValorFechamentoPix(caixa.getValorFechamentoPix() == null ? 0 : caixa.getValorFechamentoPix());
			caixa.setValorFechamentoCartaoCredito(
					caixa.getValorFechamentoCartaoCredito() == null ? 0 : caixa.getValorFechamentoCartaoCredito());
			caixa.setValorFechamentoCartaoDebito(
					caixa.getValorFechamentoCartaoDebito() == null ? 0 : caixa.getValorFechamentoCartaoDebito());

			caixa.setValorFechamento(
					caixa.getPagamentos().stream().mapToDouble(f -> f.getValorPagamento()).sum());

			return caixa;
		} else {
			return null;
		}
	}

	public List<Pagamento> pagamentosDia() {
		return pagamentoRepository.findByDataPagamento(LocalDateTime.now());
	}

	public List<Pagamento> pagamentosFindByCaixa(Long id) {
		Caixa c = new Caixa();
		c.setId(id);
		List<Pagamento> listPgamentos = pagamentoRepository.findByCaixa(c);
		return listPgamentos;
	}

//	public Caixa getCaixaUserOpen (String nome) {
//		Caixa c = caixaRepository.find
//	}

	public CaixaDTO findByCaixa(Long id) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		Optional<Caixa> c = caixaRepository.findById(id);
		Caixa a = c.get();
		CaixaDTO dto = new CaixaDTO();
		dto.setId(a.getId());
		dto.setAberto(a.isAberto());
		dto.setNome(a.getNome());
		dto.setValorAbertura(a.getValorAbertura());
		dto.setValorFechamento(a.getValorFechamento());
		dto.setDataAbertura(a.getDataAbertura().format(formatter));

		//
		dto.setValorFechamentoDinheiro(a.getValorFechamentoDinheiro());
		dto.setValorFechamentoPix(a.getValorFechamentoPix());
		dto.setValorFechamentoCartaoCredito(a.getValorFechamentoCartaoCredito());
		dto.setValorFechamentoCartaoDebito(a.getValorFechamentoCartaoDebito());
		dto.setValorFechamentoConsignado(a.getValorFechamentoConsignado());
		//
		dto.setDiferencaDinheiro(a.getDiferencaDinheiro());
		dto.setDiferencaPix(a.getDiferencaPix());
		dto.setDiferencaCartaoCredito(a.getDiferencaCartaoCredito());
		dto.setDiferencaCartaoDebito(a.getDiferencaCartaoDebito());

		dto.setDiferencaConsignado(a.getDiferencaConsignado());
		//
		dto.setValorPagamentoDinheiro(a.getValorPagamentoDinheiro());
		dto.setValorPagamentoPix(a.getValorPagamentoPix());
		dto.setValorPagamentoCartaoCredito(a.getValorPagamentoCartaoCredito());
		dto.setValorPagamentoCartaoDebito(a.getValorPagamentoCartaoDebito());
		dto.setValorPagamentoConsignado(a.getValorPagamentoConsignado());
		dto.setDataFechamento(a.getDataFechamento() != null ? a.getDataFechamento().format(formatter) : null);

		List<PagamentoDTO> listDTOpagamento = new ArrayList<PagamentoDTO>();

		a.getPagamentos().forEach(pagamento -> {
			PagamentoDTO dtop = new PagamentoDTO();
			dtop.setId(pagamento.getId());
			dtop.setIdModoPagamento(pagamento.getModoPagamento().getId());
			dtop.setValorPagamento(pagamento.getValorPagamento());
			dtop.setIdVenda(pagamento.getIdVenda());
			dtop.setQuantidadeParcela(pagamento.getQuantidadeParcela());
			dtop.setDataPagamento(pagamento.getDataPagamento());
			dtop.setTroco(pagamento.getTroco());
			dtop.setModoPagamentoDescricao(pagamento.getModoPagamento().getDescricao());
			listDTOpagamento.add(dtop);
		});
		dto.setPagamentos(listDTOpagamento);

		return dto;
	}

	public CaixaDTO findByUsuarioAberto() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		Caixa caixa = caixaRepository.findByIdUsuarioAndAberto(getIdUserLogado(), true);

		CaixaDTO dto = new CaixaDTO();
		dto.setId(caixa.getId());
		dto.setAberto(caixa.isAberto());
		dto.setNome(caixa.getNome());
		dto.setValorAbertura(caixa.getValorAbertura());
		dto.setValorFechamento(caixa.getValorFechamento());
		dto.setDataAbertura(caixa.getDataAbertura().format(formatter));

		//
		dto.setValorFechamentoDinheiro(caixa.getValorFechamentoDinheiro());
		dto.setValorFechamentoPix(caixa.getValorFechamentoPix());
		dto.setValorFechamentoCartaoCredito(caixa.getValorFechamentoCartaoCredito());
		dto.setValorFechamentoCartaoDebito(caixa.getValorFechamentoCartaoDebito());
		dto.setValorFechamentoConsignado(caixa.getValorFechamentoConsignado());
		//
		dto.setDiferencaDinheiro(caixa.getDiferencaDinheiro());
		dto.setDiferencaPix(caixa.getDiferencaPix());
		dto.setDiferencaCartaoCredito(caixa.getDiferencaCartaoCredito());
		dto.setDiferencaCartaoDebito(caixa.getDiferencaCartaoDebito());

		dto.setDiferencaConsignado(caixa.getDiferencaConsignado());
		//
		dto.setValorPagamentoDinheiro(caixa.getValorPagamentoDinheiro());
		dto.setValorPagamentoPix(caixa.getValorPagamentoPix());
		dto.setValorPagamentoCartaoCredito(caixa.getValorPagamentoCartaoCredito());
		dto.setValorPagamentoCartaoDebito(caixa.getValorPagamentoCartaoDebito());
		dto.setValorPagamentoConsignado(caixa.getValorPagamentoConsignado());
		dto.setDataFechamento(caixa.getDataFechamento() != null ? caixa.getDataFechamento().format(formatter) : null);

		List<PagamentoDTO> listDTOpagamento = new ArrayList<PagamentoDTO>();

		caixa.getPagamentos().forEach(pagamento -> {
			PagamentoDTO dtop = new PagamentoDTO();
			dtop.setId(pagamento.getId());
			dtop.setIdModoPagamento(pagamento.getModoPagamento().getId());
			dtop.setValorPagamento(pagamento.getValorPagamento());
			dtop.setIdVenda(pagamento.getIdVenda());
			dtop.setQuantidadeParcela(pagamento.getQuantidadeParcela());
			dtop.setDataPagamento(pagamento.getDataPagamento());
			dtop.setTroco(pagamento.getTroco());
			dtop.setModoPagamentoDescricao(pagamento.getModoPagamento().getDescricao());
			listDTOpagamento.add(dtop);
		});
		dto.setPagamentos(listDTOpagamento);

		return dto;
	}

	public Caixa findByUsuarioAbertoC() {
		Caixa caixa = caixaRepository.findByIdUsuarioAndAberto(getIdUserLogado(), true);
		return caixa;
	}

}
