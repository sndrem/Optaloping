package sim.tv2.no.playerTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import sim.tv2.no.comparators.DistanceComparator;
import sim.tv2.no.player.Player;

public class PlayerTest {

	private List<Player> players;
	private Player player1;
	private Player player2;
	
	@Before
	public void setUp() throws Exception {
		players = new ArrayList<Player>();
		player1 = new Player("Sindre Moldeklev", 11, 45, 12.05, 15.06, 35.7, "Everton", true);
		player2 = new Player("Christiano Ronaldo", 7, 69, 23.5, 16.05 ,34.7, "Tottenham", false);
		players.add(player1);
		players.add(player2);
	}

	@Test
	public void testCompareTo() {
		Collections.sort(players, new DistanceComparator());
		Player fastPlayer = players.get(0);
		assertEquals(23.5, fastPlayer.getDistance(), 0.001);
	}
	
	@Test
	public void testRemoveFirstName() {
		String name = "Sindre Moldeklev";
		player1.setName(name);
		String myName = player1.getName();
		String newName = player1.removeFirstName(myName);
		assertEquals("Moldeklev", newName);
	}
}
