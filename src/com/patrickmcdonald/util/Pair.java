package com.patrickmcdonald.util;

public final class Pair<F,S>{
	   private F _first;
	   private S _second;

	   public Pair(F f, S s){
		   _first = f;
		   _second = s;
	   }
	   
	   public F getFirst(){
		   return _first;
	   }
	   
	   public S getSecond(){
		   return _second;
	   }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_first == null) ? 0 : _first.hashCode());
		result = prime * result + ((_second == null) ? 0 : _second.hashCode());
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
		Pair other = (Pair) obj;
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
		return true;
	}
	   
}