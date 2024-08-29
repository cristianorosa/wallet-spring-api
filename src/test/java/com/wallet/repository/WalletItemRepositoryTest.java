package com.wallet.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import com.wallet.entity.Wallet;
import com.wallet.entity.WalletItem;
import com.wallet.enums.TypeEnum;

import jakarta.validation.ConstraintViolationException;

@SpringBootTest
@ActiveProfiles("test")
public class WalletItemRepositoryTest {
	
	private static final Date DATE = new Date();
	private static final TypeEnum TYPE = TypeEnum.EN;
	private static final String DESCRIPTION = "Conta de Luz";
	private static final BigDecimal VALUE = BigDecimal.valueOf(65);
	
	Long savedWalletItemId;
	Long savedWalletId;
	
	@Autowired
	WalletItemRepository repo;
	
	@Autowired
	WalletRepository repoWallet;
	
	@BeforeEach
	public void setUp() {
		Wallet wallet = new Wallet(null, "Carteira Teste", BigDecimal.valueOf(250));
		repoWallet.save(wallet);
		
		WalletItem wtem = new WalletItem(wallet, DATE, TYPE, DESCRIPTION, VALUE);
		repo.save(wtem);
		
		savedWalletItemId = wtem.getId();
		savedWalletId = wallet.getId();
	}
	
	@AfterEach
	public void tearDown() {
		repo.deleteAll();
		repoWallet.deleteAll();
	}
	
	@Test
	public void save() {
		Wallet wallet = new Wallet();
		wallet.setName("Carteira 1");
		wallet.setValue(BigDecimal.valueOf(500));
		repoWallet.save(wallet);
		 
		WalletItem walletItem = new WalletItem(wallet, DATE, TYPE, DESCRIPTION, VALUE);
		WalletItem response = repo.save(walletItem);
		
		assertNotNull(response);
		assertEquals(response.getDate(), DATE);
		assertEquals(response.getDescription(), DESCRIPTION);
		assertEquals(response.getValue(), VALUE);
		assertEquals(response.getType(), TYPE);
		assertEquals(response.getWallet().getId(), wallet.getId());
	}
	
	@Test
	public void saveInvalidItem() {
		Assertions.assertThrows(ConstraintViolationException.class, () -> {
			WalletItem walletItem = new WalletItem(null, DATE, TYPE, DESCRIPTION, VALUE);
			repo.save(walletItem);
		});
	}
	
	@Test
	public void update() {
		Optional<WalletItem> witem = repo.findById(savedWalletItemId);
		String description = "Descrição alterada";
		WalletItem changed = witem.get();
		
		changed.setDescription(description);
		repo.save(changed);
		
		Optional<WalletItem> newItem = repo.findById(savedWalletItemId);
		assertEquals(description, newItem.get().getDescription());
	}
	
	@Test
	public void delete() {
		Optional<Wallet> witem = repoWallet.findById(savedWalletId);
		WalletItem walletItem = new WalletItem(witem.get(), DATE, TYPE, DESCRIPTION, VALUE, null);
		repo.save(walletItem);
		
		repo.deleteById(walletItem.getId());
			
		Optional<WalletItem> newItem = repo.findById(walletItem.getId());
		assertFalse(newItem.isPresent());
	}
	
	@Test
	public void findBetweenDates() {
		Optional<Wallet> wallet = repoWallet.findById(savedWalletId);
		
		LocalDateTime lDateTime = DATE.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		
		Date currentDatePlusFiveDays = Date.from(lDateTime.plusDays(5).atZone(ZoneId.systemDefault()).toInstant());
		Date currentDatePlusSevenDays = Date.from(lDateTime.plusDays(7).atZone(ZoneId.systemDefault()).toInstant());
		
		repo.save(new WalletItem(wallet.get(), currentDatePlusFiveDays, TYPE, DESCRIPTION, VALUE, null));
		repo.save(new WalletItem(wallet.get(), currentDatePlusSevenDays, TYPE, DESCRIPTION, VALUE, null));
		
		PageRequest pr = PageRequest.of(0, 10);
		Page<WalletItem> response = repo.findAllByWalletIdAndDateGreaterThanEqualAndDateLessThanEqual(savedWalletId, DATE, currentDatePlusFiveDays, pr);
		
		assertEquals(response.getContent().size(), 2);
		assertEquals(response.getTotalElements(), 2);
		assertEquals(response.getContent().get(0).getWallet().getId(), savedWalletId);
	}
	
	@Test
	public void findByType() {
		List<WalletItem> response = repo.findByWalletIdAndType(savedWalletId, TYPE);
		
		assertEquals(response.size(), 1);
		assertEquals(response.get(0).getType(), TYPE);
	}
	
	@Test
	public void findByTypeSd() {
		Optional<Wallet> wallet = repoWallet.findById(savedWalletId);
		repo.save(new WalletItem(wallet.get(), DATE, TypeEnum.SD, DESCRIPTION, VALUE, null));
		
		List<WalletItem> response = repo.findByWalletIdAndType(savedWalletId, TypeEnum.SD);
		assertEquals(response.size(), 1);
		assertEquals(response.get(0).getType(), TypeEnum.SD);
	}

	@Test
	public void sumByWallet() {
		Optional<Wallet> wallet = repoWallet.findById(savedWalletId);
		repo.save(new WalletItem(wallet.get(), DATE, TypeEnum.SD, DESCRIPTION, BigDecimal.valueOf(150.80), null));
		
		BigDecimal response = repo.sumByWalletId(savedWalletId);
		
		assertEquals(response.compareTo(BigDecimal.valueOf(215.8)), 0);
	}
}
