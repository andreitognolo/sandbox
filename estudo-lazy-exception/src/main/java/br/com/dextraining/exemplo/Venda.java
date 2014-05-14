package br.com.dextraining.exemplo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(schema = "TE")
public class Venda {

	@Id
	@GeneratedValue
	private Long id;
	
	@JoinColumn(name = "venda_id")
	@OneToMany(cascade = CascadeType.ALL)
	private List<ItemVenda> itens = new ArrayList<ItemVenda>();

	public Long getId() {
		return id;
	}

	public List<ItemVenda> getItens() {
		return itens;
	}

	public void addItem(ItemVenda item) {
		this.itens.add(item);
	}
}
