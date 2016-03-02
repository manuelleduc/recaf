package recaf.core.alg;

import java.util.function.Function;
import java.util.function.Supplier;

import recaf.core.Ref;
import recaf.core.direct.SupplierEx;

public interface JavaStmtAlg<R, E, S, C> {
	<T> S Decl(Supplier<T> exp, Function<Ref<T>, S> body);
	
	<T> S ForEach(Supplier<Iterable<T>> exp, Function<Ref<T>, S> body);
	
	<T> S ForDecl(Supplier<T> init, Function<Ref<T>, S> body);

	S ForBody(Supplier<Boolean> cond, S update, S body);
	
	<T extends Throwable> S Throw(Supplier<T> e);

	<T extends Throwable> S TryCatch(S body, Class<T> type, Function<T, S> handle);
	
	@SuppressWarnings("unchecked")
	<T> S Switch(Supplier<T> expr, C... cases);
		
	<T> C Case(T constant, S expStat);
	
	C Default(S expStat);

	S For(S init, Supplier<Boolean> cond, S update, S body);

	S If(Supplier<Boolean> cond, S s);
	
	S If(Supplier<Boolean> cond, S s1, S s2);
	
	S While(Supplier<Boolean> cond, S s);
	
	S DoWhile(S s, Supplier<Boolean> cond);
	
	S Labeled(String label, S s);
	
	S Break(String label);
	
	S Continue(String label);
	
	S Break();
	
	S Continue();

	S Return(SupplierEx<R,?> supplier);
	
	S Return();

	S Empty();
	
	S Seq(S s1, S s2);
	
	S TryFinally(S body, S fin);
	
	S ExpStat(E e);
	
	<T> Supplier<T> Exp(E e) throws Exception;
}
