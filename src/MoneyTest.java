import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MoneyTest {
	@Test
	void testMultiplication() {
		Money five = Money.dollar(5);
		assertEquals(Money.dollar(10), five.times(2));
		assertEquals(Money.dollar(15), five.times(3));
	}
	
//	@Test
//	void testFrancMultiplication() {
//		Money five = Money.franc(5);
//		assertEquals(Money.franc(10), five.times(2));
//		assertEquals(Money.franc(15), five.times(3));
//	}
	
	@Test
	void testEquality() {
		assertTrue(Money.dollar(5).equals(Money.dollar(5)));
		assertFalse(Money.dollar(5).equals(Money.dollar(6)));
		assertFalse(Money.franc(5).equals(Money.dollar(5)));
	}
	
	@Test
	void testCurrency() {
		assertEquals("USD", Money.dollar(1).currency());
		assertEquals("CHF", Money.franc(1).currency());
	}
	
	@Test
	void testSimpleAddition() {
		Money five = Money.dollar(5);
		Expression sum = five.plus(five);
		Bank bank = new Bank();
		Money reduced = bank.reduce(sum, "USD");
		assertEquals(Money.dollar(10), reduced);
	}
	
	@Test
	void testPlusReturnsSum() {
		Money five = Money.dollar(5);
		Expression result = five.plus(five);
		Sum sum = (Sum)result;
		assertEquals(five, sum.augend);
		assertEquals(five, sum.addend);
	}
	
	@Test
	void testReduceSum() {
		Expression sum = new Sum(Money.dollar(3), Money.dollar(4));
		Bank bank = new Bank();
		Money result = bank.reduce(sum, "USD");
		assertEquals(Money.dollar(7), result);
	}
	
	@Test
	void testReduceMoney() {
		Bank bank = new Bank();
		Money result = bank.reduce(Money.dollar(1), "USD");
		assertEquals(Money.dollar(1), result);
	}
}

interface Expression {

	public Money reduce(String to);
}

class Sum implements Expression {
	Money augend;
	Money addend;
	
	Sum(Money augend, Money addend) {
		this.augend = augend;
		this.addend = addend;
	}
	
	public Money reduce(String to)
	{
		int amount = augend.amount + addend.amount;
		return new Money(amount, to);
	}
}

class Bank {
	Money reduce(Expression source, String to) {
		return source.reduce(to);
	}
}

class Money implements Expression{
	protected int amount;
	protected String currency;
	
	Money(int amount, String currency) {
		this.amount = amount;
		this.currency = currency;
	}
	
	Expression plus(Money addend) {
//		return new Money(amount + addend.amount, currency);
		return new Sum(this, addend);
	}
	
	public Money reduce(String to) {
		return this;
	}

	public boolean equals(Object object) {
		Money money = (Money) object;
		return amount == money.amount && currency().equals(money.currency());
	}
	
	Money times(int multiplier) {
		return new Money(amount * multiplier, currency);
	}

	static Money dollar(int amount) {
		return new Money(amount, "USD");
	}
	
	static Money franc(int amount) {
		return new Money(amount, "CHF");
	};
	
	String currency() {
		return currency;
	}
	
	public String toString() {
		return amount + " " + currency;
	}
}


