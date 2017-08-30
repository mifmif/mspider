/**
 * Copyright 2014 y.mifrah
 *

 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mifmif.networking.mspider.database.dao.api;

/**
 * @author y.mifrah
 *
 * @param <K>
 * @param <E>
 */
public interface Dao<K, E> {
	void persist(E entity);

	void remove(E entity);

	E merge(E entity);

	E findById(K id);
}