
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import recaf.async.AsyncExtension;

public class TestAsync {

	CompletableFuture<Integer> op(AsyncExtension<Integer> alg) {
		return (CompletableFuture<Integer>) alg.Method(alg.Seq(alg.If(alg.Exp(() -> {
			return 1 > 5;
		}), alg.Return(alg.Exp(() -> {
			return 42;
		}))), alg.Return(alg.Exp(() -> {
			return 41;
		}))));
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		Integer answer = new TestAsync().op(new AsyncExtension<Integer>()).get();

		System.out.println(answer);
	}
}