package com.wallet.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import com.wallet.entity.Wallet;
import com.wallet.entity.WalletItem;
import com.wallet.enums.TypeEnum;
import com.wallet.repository.WalletItemRepository;

@SpringBootTest
@ActiveProfiles("test")
class WalletItemServiceTest {
	
	@MockBean
	WalletItemRepository repo;
	
	@Autowired
	WalletItemService service;
	
	static final Long ID = 1L;
	static final Date DATE = new Date();
	static final TypeEnum TYPE = TypeEnum.EN;
	static final String DESCRIPTION = "Conta de Luz";
	static final BigDecimal VALUE = BigDecimal.valueOf(65);
	
	private Wallet wallet;
	
	@BeforeEach
	void setUp() {
		wallet = new Wallet(ID, "Carteira test", BigDecimal.valueOf(256.3));
	}
	
	@Test
	void save() {		
		BDDMockito.given(repo.save(Mockito.any(WalletItem.class))).willReturn(getMockWalletItem());
		
		WalletItem walletItem = service.save(new WalletItem());
		
		assertNotNull(walletItem);
		assertEquals(DESCRIPTION, walletItem.getDescription());
		assertEquals(TYPE, walletItem.getType());
		assertEquals(DATE, walletItem.getDate());
		assertEquals(VALUE, walletItem.getValue());
	}
	
	@Test
	void findBetweenDates() {
		List<WalletItem> list = new ArrayList<WalletItem>();
		list.add(getMockWalletItem());
		Page<WalletItem> page = new PageImpl<WalletItem>(list);
		
		BDDMockito.given(repo.findAllByWalletIdAndDateGreaterThanEqualAndDateLessThanEqual(Mockito.anyLong(), Mockito.any(Date.class), Mockito.any(Date.class), Mockito.any(PageRequest.class)))
			.willReturn(page);
		
		Page<WalletItem> response = service.findBetweenDates(ID, new Date(), new Date(), 0); 
		
		assertNotNull(response);
		assertEquals(1, response.getContent().size());
		assertEquals(DESCRIPTION, response.getContent().get(0).getDescription());
	}
	
	@Test
	void findByType() {
		List<WalletItem> list = new ArrayList<WalletItem>();
		list.add(getMockWalletItem());
		
		BDDMockito.given(repo.findByWalletIdAndType(Mockito.anyLong(), Mockito.any(TypeEnum.class))).willReturn(list);
		
		List<WalletItem> response = service.findByWalletAndType(ID, TypeEnum.EN);
				
		assertNotNull(response);
		assertEquals(TYPE, response.get(0).getType());
	}
	
	@Test
	void deleteById() {
		BDDMockito.given(repo.findById(Mockito.anyLong())).willReturn(Optional.of(getMockWalletItem()));
		assertDoesNotThrow(() -> {
			service.deleteById(ID);
		});
	}
	
	@Test
	void findById() {
		BDDMockito.given(repo.findById(Mockito.anyLong())).willReturn(Optional.of(getMockWalletItem()));
		
		Optional<WalletItem> response = service.findByID(ID);
				
		assertTrue(response.isPresent());
		assertEquals(ID, response.get().getId());
	}
	
	@Test
	void sumByWallet() {
		BigDecimal value = BigDecimal.valueOf(45);
		BDDMockito.given(repo.sumByWalletId(Mockito.anyLong())).willReturn(value);
		
		BigDecimal response = service.sumByWalletID(ID);
		
		assertEquals(0, response.compareTo(value));
		
	}
		
	WalletItem getMockWalletItem() {
		WalletItem wal = new WalletItem();
		wal.setWallet(wallet);
		wal.setDate(DATE);
		wal.setDescription(DESCRIPTION);
		wal.setId(ID);
		wal.setType(TYPE);
		wal.setValue(VALUE);
		
		return wal;
	}	

}
