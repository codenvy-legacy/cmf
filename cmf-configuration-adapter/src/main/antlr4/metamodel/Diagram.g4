/*
 * Copyright 2014 Codenvy, S.A.
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

grammar Diagram;

import Common;

diagram                 :
                            'Elements'  COLON
                            BEGIN
                                element (',' element)*
                            END
                            (
                                'Connections'  COLON
                                BEGIN
                                    connection (',' connection )*
                                END
                            )*
                        ;

connection              :
                            'name' COLON connectionName
                            'type' COLON connectionType
                            BEGIN
                                BEGIN
                                    connectionPair
                                END
                                (
                                    ',' BEGIN
                                        connectionPair
                                    END
                                )*
                            END
                        ;

connectionName          :
                            TEXT
                        ;

connectionType          :
                            'DIRECTED'      |
                            'NONDIRECTED'   |
                            'POSITIONAL'
                        ;

connectionPair          :
                            connectionStart ',' connectionFinish
                        ;

connectionStart         :
                            TEXT
                        ;

connectionFinish        :
                            TEXT
                        ;

element                 :
                            elementName

                            elementProperties?
                            elementComponents?
                            elementRelation?
                        ;


elementComponents       :
                            'Components' COLON
                            BEGIN
                                elementComponent (',' elementComponent)*
                            END
                        ;

elementComponent        :
                            TEXT
                        ;

elementName             :
                            'name' COLON TEXT
                        ;

elementProperties       :
                            'Properties' COLON
                            BEGIN elementProperty (',' elementProperty )* END
                        ;

elementProperty         :
                            'name'  COLON propertyName
                            'type'  COLON propertyType
                            'value' COLON propertyValue
                        ;

propertyName            :
                            TEXT
                        ;

propertyType           :
                            'BOOLEAN'   |
                            'INTEGER'   |
                            'FLOAT'     |
                            'STRING'
                        ;

propertyValue           :
                            TEXT
                        ;

elementRelation        :
                            'relation'  COLON RELATION
                        ;

RELATION                :
                            'SINGLE'    |
                            'MULTIPLE'
                        ;


