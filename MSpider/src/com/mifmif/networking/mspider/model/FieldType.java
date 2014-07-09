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
package com.mifmif.networking.mspider.model;

/**
 * The content type of a field, it could be used to decide the datastore
 * destination that will be used to save payload of the field (ex: whether we
 * save binary content in the database or in a folder and record it's path in
 * the database)
 * 
 * @author y.mifrah
 *
 */
public enum FieldType {
	LONG, DOUBLE, TEXT, IMAGE, BINARY;
}
