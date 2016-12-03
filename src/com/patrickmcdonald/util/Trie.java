package com.patrickmcdonald.util;

public final class Trie<F,S,T>{
	   
	   private F _first;
	   private S _second;
	   private T _third;
	   
	   public Trie(F f, S s, T t){
		   _first = f;
		   _second = s;
		   _third = t;
	   }
	   
	   public F getFirst(){
		   return _first;
	   }
	   
	   public S getSecond(){
		   return _second;
	   }
	   
	   
	   public T getThird(){
		   return _third;
	   }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_first == null) ? 0 : _first.hashCode());
		result = prime * result + ((_second == null) ? 0 : _second.hashCode());
		result = prime * result + ((_third == null) ? 0 : _third.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Trie other = (Trie) obj;
		if (_first == null) {
			if (other._first != null)
				return false;
		} else if (!_first.equals(other._first))
			return false;
		if (_second == null) {
			if (other._second != null)
				return false;
		} else if (!_second.equals(other._second))
			return false;
		if (_third == null) {
			if (other._third != null)
				return false;
		} else if (!_third.equals(other._third))
			return false;
		return true;
	}
	   
	   
}