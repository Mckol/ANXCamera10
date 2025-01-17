package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeOperator;
import io.reactivex.MaybeSource;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;

public final class MaybeLift<T, R> extends AbstractMaybeWithUpstream<T, R> {
    final MaybeOperator<? extends R, ? super T> operator;

    public MaybeLift(MaybeSource<T> maybeSource, MaybeOperator<? extends R, ? super T> maybeOperator) {
        super(maybeSource);
        this.operator = maybeOperator;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r2v2, resolved type: io.reactivex.MaybeSource<T> */
    /* JADX WARN: Multi-variable type inference failed */
    /* access modifiers changed from: protected */
    @Override // io.reactivex.Maybe
    public void subscribeActual(MaybeObserver<? super R> maybeObserver) {
        try {
            MaybeObserver<? super Object> apply = this.operator.apply(maybeObserver);
            ObjectHelper.requireNonNull(apply, "The operator returned a null MaybeObserver");
            this.source.subscribe(apply);
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, maybeObserver);
        }
    }
}
