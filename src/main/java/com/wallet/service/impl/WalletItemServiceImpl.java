package com.wallet.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.wallet.entity.WalletItem;
import com.wallet.enums.TypeEnum;
import com.wallet.repository.WalletItemRepository;
import com.wallet.service.WalletItemService;

@Service
public class WalletItemServiceImpl implements WalletItemService {
	
	@Autowired
	WalletItemRepository repo;
	
	@Value("${pagination.items_per_page}")
	private int items_per_page;

	@Override
	public WalletItem save(WalletItem u) {
		return repo.save(u);
	}

	@Override
	public Page<WalletItem> findBetweenDates(long id, Date date_1, Date date_2, int page) {
		return repo.findAllByWalletIdAndDateGreaterThanEqualAndDateLessThanEqual(id, date_1, date_2, PageRequest.of(page, items_per_page));
	}

	@Override
	public List<WalletItem> findByWalletAndType(long id, TypeEnum type) {
		return repo.findByWalletIdAndType(id, type);
	}

	@Override
	public BigDecimal sumByWalletID(long id) {
		return repo.sumByWalletId(id);
	}

	@Override
	public Optional<WalletItem> findByID(long id) {		
		return repo.findById(id);
	}

	@Override
	public void deleteById(Long id) {
		Optional<WalletItem> walletItem = repo.findById(id);
		if (walletItem.isPresent()) {
			repo.delete(walletItem.get());
		}
	}

}
