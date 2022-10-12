import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MoneyTest {
	@Test
	void testMultiplication() {
		Money five = Money.dollar(5);
		assertEquals(Money.dollar(10), five.times(2));
		assertEquals(Money.dollar(15), five.times(3));
	}
	
	@Test
	void testEquality() {
		assertTrue(Money.dollar(5).equals(Money.dollar(5)));
		assertFalse(Money.dollar(5).equals(Money.dollar(6)));
		assertFalse(Money.franc(5).equals(Money.franc(6)));
	}
	
	void testCurrency() {
		assertEquals("USD", Money.dollar(1).currency());
		assertEquals("CHF", Money.franc(1).currency());
	}
}

abstract class Money {
	protected int amount;
	protected String currency;
	
	Money(int amount, String currency) {
		this.amount = amount;
		this.currency = currency;
	}
	
	public boolean equals(Object object) {
		Money money = (Money) object;
		return amount == money.amount && getClass().equals(money.getClass());
	}
	
	abstract Money times(int multiplier);

	static Money dollar(int amount) {
		return new Dollar(amount, "USD");
	}
	
	static Franc franc(int amount) {
		return new Franc(amount, "CHF");
	};
	
	String currency() {
		return currency;
	}
}

class Dollar extends Money {
	Dollar(int amount, String currency) {
		super(amount, currency);
	}
	
	Money times(int multiplier) {
		return Money.dollar(amount * multiplier);
	}
}

class Franc extends Money{
	Franc(int amount, String currency) {
		super(amount, currency);
	}
	
	Money times(int multiplier) {
		return Money.franc(amount * multiplier);
	}
}


