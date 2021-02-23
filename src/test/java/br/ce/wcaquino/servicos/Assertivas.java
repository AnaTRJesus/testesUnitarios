package br.ce.wcaquino.servicos;

import org.junit.Assert;
import org.junit.Test;

import br.ce.wcaquino.entidades.Usuario;

public class Assertivas {
	
	@Test
	public void testar() {
		Assert.assertTrue(true);
		Assert.assertFalse(false);
		
		Assert.assertEquals(1, 1);
		Assert.assertEquals(15.0223, 15.2134, 01.0);
		
		int i = 30;
		Integer j = 30;
		
		Assert.assertEquals(i,j.intValue());
		Assert.assertTrue("bola".equalsIgnoreCase("Bola"));
		Assert.assertTrue("bola".startsWith("bo"));
		
		Usuario usuario01 = new Usuario();
		Usuario usuario02 = new Usuario();
		Usuario usuario03 = usuario01;
		Usuario usuario04 = null;
		
		Assert.assertNotEquals(usuario01, usuario04);
		Assert.assertEquals(usuario01, usuario02);

		Assert.assertNull(usuario04);
		Assert.assertNotNull(usuario03);
		
		Assert.assertSame(usuario01, usuario03);
		Assert.assertNotSame(usuario01, usuario02);
			
	}

}
