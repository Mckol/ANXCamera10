package io.reactivex.internal.operators.flowable;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.operators.flowable.FlowableReduceSeedSingle;
import java.util.concurrent.Callable;
import org.reactivestreams.Publisher;

public final class FlowableReduceWithSingle<T, R> extends Single<R> {
    final BiFunction<R, ? super T, R> reducer;
    final Callable<R> seedSupplier;
    final Publisher<T> source;

    public FlowableReduceWithSingle(Publisher<T> publisher, Callable<R> callable, BiFunction<R, ? super T, R> biFunction) {
        this.source = publisher;
        this.seedSupplier = callable;
        this.reducer = biFunction;
    }

    /* access modifiers changed from: protected */
    @Override // io.reactivex.Single
    public void subscribeActual(SingleObserver<? super R> singleObserver) {
        try {
            R call = this.seedSupplier.call();
            ObjectHelper.requireNonNull(call, "The seedSupplier returned a null value");
            this.source.subscribe(new FlowableReduceSeedSingle.ReduceSeedObserver(singleObserver, this.reducer, call));
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, singleObserver);
        }
    }
}
