package com.test;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * User: Created by zhanghongqiang
 * Date: 2016-01-15 Time: 15:21
 * ToDo:http://nerds.weddingpartyapp.com/tech/2014/12/24/implementing-an-event-bus-with-rxjava-rxbus/
 * 使用：
 */
public class RxBus {

    private final Subject<Object, Object> _bus = new SerializedSubject<>(
            PublishSubject.create());


    public void send(Object o) {
        _bus.onNext(o);
    }


    public Observable<Object> toObserverable() {
        return _bus;
    }
}
