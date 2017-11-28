// Copyright 2000-2017 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package org.jetbrains.java.decompiler.struct.consts;

import org.jetbrains.java.decompiler.code.CodeConstants;

import java.io.DataOutputStream;
import java.io.IOException;

/*
    cp_info {
    	u1 tag;
    	u1 info[];
    }

*/

public class PooledConstant implements CodeConstants, VariableTypeEnum {

  // *****************************************************************************
  // public fields
  // *****************************************************************************

  public int type;

  public boolean own = false;

  public int returnType;


  // *****************************************************************************
  // private fields
  // *****************************************************************************

  private Object[] values;

  // *****************************************************************************
  // constructors
  // *****************************************************************************

  public PooledConstant() {
  }

  public PooledConstant(int type, Object[] values) {
    this.type = type;
    this.values = values;
    this.returnType = poolTypeToIntern(type);
  }

  public PooledConstant(int type, boolean own, Object[] values) {
    this.type = type;
    this.own = own;
    this.values = values;
    this.returnType = poolTypeToIntern(type);
  }

  // *****************************************************************************
  // public methods
  // *****************************************************************************

  public void resolveConstant(ConstantPool pool) {
    // to be overwritten
  }

  public void writeToStream(DataOutputStream out) throws IOException {
    // to be overwritten
  }

  public int poolTypeToIntern(int type) {

    switch (type) {
      case CONSTANT_Integer:
        return INT;
      case CONSTANT_Float:
        return FLOAT;
      case CONSTANT_Long:
        return LONG;
      case CONSTANT_Double:
        return DOUBLE;
      case CONSTANT_String:
      case CONSTANT_Class:  // 1.5 -> ldc class
        return REFERENCE;
      default:
        throw new RuntimeException("Huh?? What are you trying to load?");
    }
  }

  public Object getValue(int index) {
    return values[index];
  }


  // *****************************************************************************
  // getter and setter methods
  // *****************************************************************************

  public Object[] getValues() {
    return values;
  }

  public void setValues(Object[] values) {
    this.values = values;
  }
}
