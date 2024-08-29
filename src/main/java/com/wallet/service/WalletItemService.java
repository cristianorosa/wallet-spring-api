package com.wallet.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.wallet.entity.WalletItem;
import com.wallet.enums.TypeEnum;

public interface WalletItemService {
	
	WalletItem save(WalletItem u);

	Page<WalletItem> findBetweenDates(long l, Date date, Date date2, int i);

	List<WalletItem> findByWalletAndType(long l, TypeEnum en);

	BigDecimal sumByWalletID(long l);

	Optional<WalletItem> findByID(long anyLong);
	
	void deleteById(Long id);
}
