export interface Action<T = any> {
  type: T
}
export interface AnyAction extends Action {
  [extraProps: string]: any
}
// 两种定义Reducer的方式，interface和type
export interface Reducer<S = any, A extends Action = AnyAction> {
  (state: S | undefined, action: A): S
}
export type Reducer2<S = any, A extends Action = AnyAction> = (
  state: S | undefined,
  action: A
) => S
export type Dispatch<A extends Action = AnyAction> = (action: A) => A
export type Listener = () => void
export type Subscribe = (listener: Listener) => Unsubscribe
export type Unsubscribe = () => void
export interface Store<S = any, A extends Action = AnyAction> {
  dispatch: Dispatch,
  getState(): S,
  subscribe(listener: Listener): Unsubscribe
}

export interface StoreCreator {
  <S, A extends Action<any>, Ext, StateExt>(
    reducer: Reducer<S, A>,
    preloadedState?: S
  ): Store<S, A>
}

export type StoreCreator1 = <S, A extends Action<any>, Ext, StateExt> (
  reducer: Reducer<S, A>,
  preloadedState?: S
) => Store<S, A>

const createStore: StoreCreator = <S, A extends Action<any>, Ext, StateExt> (
  reducer: Reducer<S, A>, preloadedState?: S | undefined
): Store<S, A> => {
  let currentState: S | undefined = preloadedState
  let currentListeners: Array<Listener> = []
  function getState (): S | undefined {
    return currentState
  }
  const dispatch: Dispatch<A> = (action: A): A => {
    currentState = reducer(currentState, action)
    currentListeners.forEach(l => l())
    return action
  }
  dispatch({ type: '@@REDUX/INIT' } as A)
  const subscribe: Subscribe = (listener: Listener): Unsubscribe => {
    currentListeners.push(listener)
    return function () {
      let index = currentListeners.indexOf(listener)
      currentListeners.splice(index, 1)
    }
  }
  const store: Store<S, A> = {
    getState,
    dispatch,
    subscribe
  }
  return store
}