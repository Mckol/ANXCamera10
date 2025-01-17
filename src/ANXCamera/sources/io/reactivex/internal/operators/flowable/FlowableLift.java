package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableOperator;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;

public final class FlowableLift<R, T> extends AbstractFlowableWithUpstream<T, R> {
    final FlowableOperator<? extends R, ? super T> operator;

    public FlowableLift(Flowable<T> flowable, FlowableOperator<? extends R, ? super T> flowableOperator) {
        super(flowable);
        this.operator = flowableOperator;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r2v6, resolved type: io.reactivex.Flowable<T> */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super R> subscriber) {
        try {
            Subscriber<? super Object> apply = this.operator.apply(subscriber);
            if (apply != null) {
                this.source.subscribe(apply);
                return;
            }
            throw new NullPointerException("Operator " + this.operator + " returned a null Subscriber");
        } catch (NullPointerException e2) {
            throw e2;
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            RxJavaPlugins.onError(th);
            NullPointerException nullPointerException = new NullPointerException("Actually not, but can't throw other exceptions due to RS");
            nullPointerException.initCause(th);
            throw nullPointerException;
        }
    }
}
