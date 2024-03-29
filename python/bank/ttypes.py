#
# Autogenerated by Thrift Compiler (0.11.0)
#
# DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
#
#  options string: py
#

from thrift.Thrift import TType, TMessageType, TFrozenDict, TException, TApplicationException
from thrift.protocol.TProtocol import TProtocolException
from thrift.TRecursive import fix_spec

import sys

from thrift.transport import TTransport
all_structs = []


class CurrencyType(object):
    PLN = 0
    EUR = 1
    USD = 2
    CHF = 3

    _VALUES_TO_NAMES = {
        0: "PLN",
        1: "EUR",
        2: "USD",
        3: "CHF",
    }

    _NAMES_TO_VALUES = {
        "PLN": 0,
        "EUR": 1,
        "USD": 2,
        "CHF": 3,
    }


class Money(object):
    """
    Attributes:
     - value
     - currency
    """


    def __init__(self, value=None, currency=None,):
        self.value = value
        self.currency = currency

    def read(self, iprot):
        if iprot._fast_decode is not None and isinstance(iprot.trans, TTransport.CReadableTransport) and self.thrift_spec is not None:
            iprot._fast_decode(self, iprot, [self.__class__, self.thrift_spec])
            return
        iprot.readStructBegin()
        while True:
            (fname, ftype, fid) = iprot.readFieldBegin()
            if ftype == TType.STOP:
                break
            if fid == 1:
                if ftype == TType.DOUBLE:
                    self.value = iprot.readDouble()
                else:
                    iprot.skip(ftype)
            elif fid == 2:
                if ftype == TType.I32:
                    self.currency = iprot.readI32()
                else:
                    iprot.skip(ftype)
            else:
                iprot.skip(ftype)
            iprot.readFieldEnd()
        iprot.readStructEnd()

    def write(self, oprot):
        if oprot._fast_encode is not None and self.thrift_spec is not None:
            oprot.trans.write(oprot._fast_encode(self, [self.__class__, self.thrift_spec]))
            return
        oprot.writeStructBegin('Money')
        if self.value is not None:
            oprot.writeFieldBegin('value', TType.DOUBLE, 1)
            oprot.writeDouble(self.value)
            oprot.writeFieldEnd()
        if self.currency is not None:
            oprot.writeFieldBegin('currency', TType.I32, 2)
            oprot.writeI32(self.currency)
            oprot.writeFieldEnd()
        oprot.writeFieldStop()
        oprot.writeStructEnd()

    def validate(self):
        return

    def __repr__(self):
        L = ['%s=%r' % (key, value)
             for key, value in self.__dict__.items()]
        return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

    def __eq__(self, other):
        return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

    def __ne__(self, other):
        return not (self == other)


class LoanRequest(object):
    """
    Attributes:
     - value
     - months
    """


    def __init__(self, value=None, months=None,):
        self.value = value
        self.months = months

    def read(self, iprot):
        if iprot._fast_decode is not None and isinstance(iprot.trans, TTransport.CReadableTransport) and self.thrift_spec is not None:
            iprot._fast_decode(self, iprot, [self.__class__, self.thrift_spec])
            return
        iprot.readStructBegin()
        while True:
            (fname, ftype, fid) = iprot.readFieldBegin()
            if ftype == TType.STOP:
                break
            if fid == 1:
                if ftype == TType.STRUCT:
                    self.value = Money()
                    self.value.read(iprot)
                else:
                    iprot.skip(ftype)
            elif fid == 2:
                if ftype == TType.I32:
                    self.months = iprot.readI32()
                else:
                    iprot.skip(ftype)
            else:
                iprot.skip(ftype)
            iprot.readFieldEnd()
        iprot.readStructEnd()

    def write(self, oprot):
        if oprot._fast_encode is not None and self.thrift_spec is not None:
            oprot.trans.write(oprot._fast_encode(self, [self.__class__, self.thrift_spec]))
            return
        oprot.writeStructBegin('LoanRequest')
        if self.value is not None:
            oprot.writeFieldBegin('value', TType.STRUCT, 1)
            self.value.write(oprot)
            oprot.writeFieldEnd()
        if self.months is not None:
            oprot.writeFieldBegin('months', TType.I32, 2)
            oprot.writeI32(self.months)
            oprot.writeFieldEnd()
        oprot.writeFieldStop()
        oprot.writeStructEnd()

    def validate(self):
        return

    def __repr__(self):
        L = ['%s=%r' % (key, value)
             for key, value in self.__dict__.items()]
        return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

    def __eq__(self, other):
        return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

    def __ne__(self, other):
        return not (self == other)


class LoanResponse(object):
    """
    Attributes:
     - foreignCurrencyCost
     - localCurrencyCost
    """


    def __init__(self, foreignCurrencyCost=None, localCurrencyCost=None,):
        self.foreignCurrencyCost = foreignCurrencyCost
        self.localCurrencyCost = localCurrencyCost

    def read(self, iprot):
        if iprot._fast_decode is not None and isinstance(iprot.trans, TTransport.CReadableTransport) and self.thrift_spec is not None:
            iprot._fast_decode(self, iprot, [self.__class__, self.thrift_spec])
            return
        iprot.readStructBegin()
        while True:
            (fname, ftype, fid) = iprot.readFieldBegin()
            if ftype == TType.STOP:
                break
            if fid == 1:
                if ftype == TType.STRUCT:
                    self.foreignCurrencyCost = Money()
                    self.foreignCurrencyCost.read(iprot)
                else:
                    iprot.skip(ftype)
            elif fid == 2:
                if ftype == TType.STRUCT:
                    self.localCurrencyCost = Money()
                    self.localCurrencyCost.read(iprot)
                else:
                    iprot.skip(ftype)
            else:
                iprot.skip(ftype)
            iprot.readFieldEnd()
        iprot.readStructEnd()

    def write(self, oprot):
        if oprot._fast_encode is not None and self.thrift_spec is not None:
            oprot.trans.write(oprot._fast_encode(self, [self.__class__, self.thrift_spec]))
            return
        oprot.writeStructBegin('LoanResponse')
        if self.foreignCurrencyCost is not None:
            oprot.writeFieldBegin('foreignCurrencyCost', TType.STRUCT, 1)
            self.foreignCurrencyCost.write(oprot)
            oprot.writeFieldEnd()
        if self.localCurrencyCost is not None:
            oprot.writeFieldBegin('localCurrencyCost', TType.STRUCT, 2)
            self.localCurrencyCost.write(oprot)
            oprot.writeFieldEnd()
        oprot.writeFieldStop()
        oprot.writeStructEnd()

    def validate(self):
        return

    def __repr__(self):
        L = ['%s=%r' % (key, value)
             for key, value in self.__dict__.items()]
        return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

    def __eq__(self, other):
        return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

    def __ne__(self, other):
        return not (self == other)


class AccountData(object):
    """
    Attributes:
     - firstName
     - lastName
     - pesel
     - income
    """


    def __init__(self, firstName=None, lastName=None, pesel=None, income=None,):
        self.firstName = firstName
        self.lastName = lastName
        self.pesel = pesel
        self.income = income

    def read(self, iprot):
        if iprot._fast_decode is not None and isinstance(iprot.trans, TTransport.CReadableTransport) and self.thrift_spec is not None:
            iprot._fast_decode(self, iprot, [self.__class__, self.thrift_spec])
            return
        iprot.readStructBegin()
        while True:
            (fname, ftype, fid) = iprot.readFieldBegin()
            if ftype == TType.STOP:
                break
            if fid == 1:
                if ftype == TType.STRING:
                    self.firstName = iprot.readString().decode('utf-8') if sys.version_info[0] == 2 else iprot.readString()
                else:
                    iprot.skip(ftype)
            elif fid == 2:
                if ftype == TType.STRING:
                    self.lastName = iprot.readString().decode('utf-8') if sys.version_info[0] == 2 else iprot.readString()
                else:
                    iprot.skip(ftype)
            elif fid == 3:
                if ftype == TType.STRING:
                    self.pesel = iprot.readString().decode('utf-8') if sys.version_info[0] == 2 else iprot.readString()
                else:
                    iprot.skip(ftype)
            elif fid == 4:
                if ftype == TType.STRUCT:
                    self.income = Money()
                    self.income.read(iprot)
                else:
                    iprot.skip(ftype)
            else:
                iprot.skip(ftype)
            iprot.readFieldEnd()
        iprot.readStructEnd()

    def write(self, oprot):
        if oprot._fast_encode is not None and self.thrift_spec is not None:
            oprot.trans.write(oprot._fast_encode(self, [self.__class__, self.thrift_spec]))
            return
        oprot.writeStructBegin('AccountData')
        if self.firstName is not None:
            oprot.writeFieldBegin('firstName', TType.STRING, 1)
            oprot.writeString(self.firstName.encode('utf-8') if sys.version_info[0] == 2 else self.firstName)
            oprot.writeFieldEnd()
        if self.lastName is not None:
            oprot.writeFieldBegin('lastName', TType.STRING, 2)
            oprot.writeString(self.lastName.encode('utf-8') if sys.version_info[0] == 2 else self.lastName)
            oprot.writeFieldEnd()
        if self.pesel is not None:
            oprot.writeFieldBegin('pesel', TType.STRING, 3)
            oprot.writeString(self.pesel.encode('utf-8') if sys.version_info[0] == 2 else self.pesel)
            oprot.writeFieldEnd()
        if self.income is not None:
            oprot.writeFieldBegin('income', TType.STRUCT, 4)
            self.income.write(oprot)
            oprot.writeFieldEnd()
        oprot.writeFieldStop()
        oprot.writeStructEnd()

    def validate(self):
        return

    def __repr__(self):
        L = ['%s=%r' % (key, value)
             for key, value in self.__dict__.items()]
        return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

    def __eq__(self, other):
        return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

    def __ne__(self, other):
        return not (self == other)


class AccountConfirmation(object):
    """
    Attributes:
     - userID
     - isPremium
    """


    def __init__(self, userID=None, isPremium=None,):
        self.userID = userID
        self.isPremium = isPremium

    def read(self, iprot):
        if iprot._fast_decode is not None and isinstance(iprot.trans, TTransport.CReadableTransport) and self.thrift_spec is not None:
            iprot._fast_decode(self, iprot, [self.__class__, self.thrift_spec])
            return
        iprot.readStructBegin()
        while True:
            (fname, ftype, fid) = iprot.readFieldBegin()
            if ftype == TType.STOP:
                break
            if fid == 1:
                if ftype == TType.STRING:
                    self.userID = iprot.readString().decode('utf-8') if sys.version_info[0] == 2 else iprot.readString()
                else:
                    iprot.skip(ftype)
            elif fid == 2:
                if ftype == TType.BOOL:
                    self.isPremium = iprot.readBool()
                else:
                    iprot.skip(ftype)
            else:
                iprot.skip(ftype)
            iprot.readFieldEnd()
        iprot.readStructEnd()

    def write(self, oprot):
        if oprot._fast_encode is not None and self.thrift_spec is not None:
            oprot.trans.write(oprot._fast_encode(self, [self.__class__, self.thrift_spec]))
            return
        oprot.writeStructBegin('AccountConfirmation')
        if self.userID is not None:
            oprot.writeFieldBegin('userID', TType.STRING, 1)
            oprot.writeString(self.userID.encode('utf-8') if sys.version_info[0] == 2 else self.userID)
            oprot.writeFieldEnd()
        if self.isPremium is not None:
            oprot.writeFieldBegin('isPremium', TType.BOOL, 2)
            oprot.writeBool(self.isPremium)
            oprot.writeFieldEnd()
        oprot.writeFieldStop()
        oprot.writeStructEnd()

    def validate(self):
        return

    def __repr__(self):
        L = ['%s=%r' % (key, value)
             for key, value in self.__dict__.items()]
        return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

    def __eq__(self, other):
        return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

    def __ne__(self, other):
        return not (self == other)


class NotSupportedCurrencyException(TException):


    def read(self, iprot):
        if iprot._fast_decode is not None and isinstance(iprot.trans, TTransport.CReadableTransport) and self.thrift_spec is not None:
            iprot._fast_decode(self, iprot, [self.__class__, self.thrift_spec])
            return
        iprot.readStructBegin()
        while True:
            (fname, ftype, fid) = iprot.readFieldBegin()
            if ftype == TType.STOP:
                break
            else:
                iprot.skip(ftype)
            iprot.readFieldEnd()
        iprot.readStructEnd()

    def write(self, oprot):
        if oprot._fast_encode is not None and self.thrift_spec is not None:
            oprot.trans.write(oprot._fast_encode(self, [self.__class__, self.thrift_spec]))
            return
        oprot.writeStructBegin('NotSupportedCurrencyException')
        oprot.writeFieldStop()
        oprot.writeStructEnd()

    def validate(self):
        return

    def __str__(self):
        return repr(self)

    def __repr__(self):
        L = ['%s=%r' % (key, value)
             for key, value in self.__dict__.items()]
        return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

    def __eq__(self, other):
        return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

    def __ne__(self, other):
        return not (self == other)


class AuthenticationException(TException):


    def read(self, iprot):
        if iprot._fast_decode is not None and isinstance(iprot.trans, TTransport.CReadableTransport) and self.thrift_spec is not None:
            iprot._fast_decode(self, iprot, [self.__class__, self.thrift_spec])
            return
        iprot.readStructBegin()
        while True:
            (fname, ftype, fid) = iprot.readFieldBegin()
            if ftype == TType.STOP:
                break
            else:
                iprot.skip(ftype)
            iprot.readFieldEnd()
        iprot.readStructEnd()

    def write(self, oprot):
        if oprot._fast_encode is not None and self.thrift_spec is not None:
            oprot.trans.write(oprot._fast_encode(self, [self.__class__, self.thrift_spec]))
            return
        oprot.writeStructBegin('AuthenticationException')
        oprot.writeFieldStop()
        oprot.writeStructEnd()

    def validate(self):
        return

    def __str__(self):
        return repr(self)

    def __repr__(self):
        L = ['%s=%r' % (key, value)
             for key, value in self.__dict__.items()]
        return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

    def __eq__(self, other):
        return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

    def __ne__(self, other):
        return not (self == other)


class AccountExistsException(TException):


    def read(self, iprot):
        if iprot._fast_decode is not None and isinstance(iprot.trans, TTransport.CReadableTransport) and self.thrift_spec is not None:
            iprot._fast_decode(self, iprot, [self.__class__, self.thrift_spec])
            return
        iprot.readStructBegin()
        while True:
            (fname, ftype, fid) = iprot.readFieldBegin()
            if ftype == TType.STOP:
                break
            else:
                iprot.skip(ftype)
            iprot.readFieldEnd()
        iprot.readStructEnd()

    def write(self, oprot):
        if oprot._fast_encode is not None and self.thrift_spec is not None:
            oprot.trans.write(oprot._fast_encode(self, [self.__class__, self.thrift_spec]))
            return
        oprot.writeStructBegin('AccountExistsException')
        oprot.writeFieldStop()
        oprot.writeStructEnd()

    def validate(self):
        return

    def __str__(self):
        return repr(self)

    def __repr__(self):
        L = ['%s=%r' % (key, value)
             for key, value in self.__dict__.items()]
        return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

    def __eq__(self, other):
        return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

    def __ne__(self, other):
        return not (self == other)
all_structs.append(Money)
Money.thrift_spec = (
    None,  # 0
    (1, TType.DOUBLE, 'value', None, None, ),  # 1
    (2, TType.I32, 'currency', None, None, ),  # 2
)
all_structs.append(LoanRequest)
LoanRequest.thrift_spec = (
    None,  # 0
    (1, TType.STRUCT, 'value', [Money, None], None, ),  # 1
    (2, TType.I32, 'months', None, None, ),  # 2
)
all_structs.append(LoanResponse)
LoanResponse.thrift_spec = (
    None,  # 0
    (1, TType.STRUCT, 'foreignCurrencyCost', [Money, None], None, ),  # 1
    (2, TType.STRUCT, 'localCurrencyCost', [Money, None], None, ),  # 2
)
all_structs.append(AccountData)
AccountData.thrift_spec = (
    None,  # 0
    (1, TType.STRING, 'firstName', 'UTF8', None, ),  # 1
    (2, TType.STRING, 'lastName', 'UTF8', None, ),  # 2
    (3, TType.STRING, 'pesel', 'UTF8', None, ),  # 3
    (4, TType.STRUCT, 'income', [Money, None], None, ),  # 4
)
all_structs.append(AccountConfirmation)
AccountConfirmation.thrift_spec = (
    None,  # 0
    (1, TType.STRING, 'userID', 'UTF8', None, ),  # 1
    (2, TType.BOOL, 'isPremium', None, None, ),  # 2
)
all_structs.append(NotSupportedCurrencyException)
NotSupportedCurrencyException.thrift_spec = (
)
all_structs.append(AuthenticationException)
AuthenticationException.thrift_spec = (
)
all_structs.append(AccountExistsException)
AccountExistsException.thrift_spec = (
)
fix_spec(all_structs)
del all_structs
