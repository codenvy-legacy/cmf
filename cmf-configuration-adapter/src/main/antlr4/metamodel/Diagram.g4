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
                            (BEGIN element END) (','  BEGIN element END)*
                        ;

element                 :
                            elementName

                            elementProperties?
                            elementComponents?
                            elementConnections?
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
                            (BEGIN elementProperty END ) (',' BEGIN elementProperty END )*
                        ;

elementProperty         :
                            'name'  COLON propertyName
                            'type'  COLON propertyType
                            'value' COLON propertyValue
                        ;

propertyName            :
                            TEXT
                        ;
propertyType            :
                            'BOOLEAN'   |
                            'INTEGER'   |
                            'FLOAT'     |
                            'STRING'
                        ;
propertyValue           :
                            TEXT
                        ;

elementConnections      :
                            'Connections' COLON
                            ( BEGIN elementConnection END) (COLON BEGIN elementConnection END)*
                        ;

elementConnection       :
                            'name'          COLON connectionName
                            'destination'   COLON connectionDestination
                            'type'          COLON connectionType
                            'relation'      COLON connectionRelation
                        ;

connectionName          :
                            TEXT
                        ;

connectionDestination   :
                            TEXT
                        ;

connectionType          :
                            'DIRECTED'      |
                            'NONDIRECTED'   |
                            'POSITIONAL'
                        ;

connectionRelation      :
                            'SINGLE'    |
                            'MULTIPLE'
                        ;