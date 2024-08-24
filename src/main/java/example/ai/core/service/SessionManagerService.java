package example.ai.core.service;

import org.apache.ibatis.session.SqlSession;

import example.ai.core.type.ThrowingFunction;

public interface SessionManagerService {

	<R> R performActionWithSession(ThrowingFunction<SqlSession, R, Throwable> action) throws Throwable;

}
