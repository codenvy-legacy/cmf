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
                            'Elements'
                            BEGIN
                                element+
                            END
                            (
                                'Connections'
                                 BEGIN
                                    connection+
                                 END
                            )?
                        ;

element                 :
                            elementName
                            BEGIN
                                elementBody
                            END
                        ;
connection              :
                            connectionName
                            BEGIN
                                connectionBody
                            END
                        ;

connectionBody          :
                            connectionType
                            'Pairs'
                            BEGIN
                                (
                                    BEGIN
                                        connectionPair
                                    END
                                )+
                            END
                        ;

connectionName          :
                            TEXT
                        ;

connectionType          :   'Type'
                            BEGIN
                                CONNECTION_TYPE
                            END
                        ;

CONNECTION_TYPE         :
                            'DIRECTED'      |
                            'NONDIRECTED'   |
                            'POSITIONAL'
                        ;

connectionPair          :
                            connectionStart '<>' connectionFinish
                        ;

connectionStart         :
                            TEXT
                        ;

connectionFinish        :
                            TEXT
                        ;

elementBody             :
                            elementRelation?
                            elementProperties?
                            elementComponents?
                        ;


elementComponents       :
                            'Components'
                            BEGIN
                                (
                                    BEGIN
                                        elementComponent
                                    END
                                )+
                            END
                        ;

elementComponent        :
                            TEXT
                        ;

elementName             :
                            TEXT
                        ;

elementProperties       :
                            'Properties'
                            BEGIN
                                elementProperty+
                            END
                        ;

elementProperty         :
                            propertyName
                            BEGIN
                                propertyBody
                            END
                        ;

propertyBody             :
                            'Type'
                            BEGIN
                                propertyType
                            END
                            'Value'
                            BEGIN
                                propertyValue
                            END
                        ;

propertyName            :
                            TEXT
                        ;

propertyType            :
                            'BOOLEAN'   |
                            'boolean'   |

                            'INTEGER'   |
                            'integer'   |

                            'FLOAT'     |
                            'float'     |

                            'STRING'    |
                            'string'
                        ;

propertyValue           :
                            TEXT
                        ;

elementRelation         :
                            'Relation'
                             BEGIN
                                RELATION
                             END
                        ;

RELATION                :
                            'SINGLE'    |
                            'single'    |
                            'MULTIPLE'  |
                            'multiple'
                        ;