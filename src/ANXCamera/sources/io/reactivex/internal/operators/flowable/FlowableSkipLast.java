package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import java.util.ArrayDeque;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableSkipLast<T> extends AbstractFlowableWithUpstream<T, T> {
    final int skip;

    static final class SkipLastSubscriber<T> extends ArrayDeque<T> implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = -3807491841935125653L;
        final Subscriber<? super T> actual;
        Subscription s;
        final int skip;

        SkipLastSubscriber(Subscriber<? super T> subscriber, int i) {
            super(i);
            this.actual = subscriber;
            this.skip = i;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.s.cancel();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.actual.onComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.actual.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.skip == size()) {
                this.actual.onNext((Object) poll());
            } else {
                this.s.request(1);
            }
            offer(t);
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.s, subscription)) {
                this.s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.s.request(j);
        }
    }

    public FlowableSkipLast(Flowable<T> flowable, int i) {
        super(flowable);
        this.skip = i;
    }

    /* access modifiers changed from: protected */
    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe((FlowableSubscriber) new SkipLastSubscriber(subscriber, this.skip));
    }
}
