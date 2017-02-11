'use strict';

angular.module('stepApp')
    .factory('EmployeeLoanRequisitionForm', function ($resource, DateUtils) {
        return $resource('api/employeeLoanRequisitionForms/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    })

    .factory('EmployeeLoanRulesList', function ($resource) {
        return $resource('api/employeeLoanRulesSetups/findLoanRulesSetup/:loanTypeID', {}, {
        'query': {method: 'GET', isArray: true},
        'get': {
        method: 'GET',
        transformResponse: function (data) {
        if (data != null){
        console.log(data);
        }
        data = angular.fromJson(data);
        return data;
        }
        }
        });
    })

    .factory('ValidateLoanAmountAndBasicSalary', function ($resource) {
        return $resource('api/employeeLoanRequisitionForms/validateLoanAmountAndBasicSalary/:loanRuleId/:amount/:employeeInfoId', {}, {
            'get': {
                  method: 'GET',
                  transformResponse: function (data) {
                      // data = angular.fromJson(data);
                      return data;
                  }
            }
        });
    })

    .factory('ValidateLoanInstallment', function ($resource) {
            return $resource('api/employeeLoanRulesSetups/findLoanRulesSetupById/:loanRulesId', {}, {
            // 'query': {method: 'GET', isArray: false},
            'get': {
                 method: 'GET',
                transformResponse: function (data) {
                data = angular.fromJson(data);
                return data;
                }
            }
            });
    })

    .factory('findLoanRequisitionDataByHrEmpID', function ($resource) {
        return $resource('api/employeeLoanRequisitionForms/findLoanRequisitionDataByHrEmpID/:employeeInfoId/:approveStatus', {}, {
            // 'query': {method: 'GET', isArray: true},
            'get': {
            method: 'GET',
                transformResponse: function (data) {
                data = angular.fromJson(data);
                return data;
                }
            }
        });
    })
     .factory('searchLoanRequisitionDataByHrEmpID', function ($resource) {
            return $resource('api/employeeLoanRequisitionForms/searchRequisitionDataByHrEmpID/:employeeInfoID', {}, {
                // 'query': {method: 'GET', isArray: true},
                'get': {
                    method: 'GET',
                    transformResponse: function (data) {
                    if (data != null){
                    console.log(data);
                    }
                    data = angular.fromJson(data);
                    return data;
                }
                }
            });
    })

    .factory('FindLoanRequisitionDataByInstitute', function ($resource) {
        return $resource('api/employeeLoanRequisitionForms/RequisitionDataByInstitute', {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                if (data != null){
                console.log(data);
                }
                data = angular.fromJson(data);
                return data;
            }
            }
        });
    })
     .factory('FindLoanPendingDataForApprove', function ($resource) {
            return $resource('api/employeeLoanRequisitionForms/getLoanPendingDataForApprove/:applyType', {}, {
                'query': {method: 'GET', isArray: true}
            });
    })
    .factory('FindLoanRequisitionByApproveStatus', function ($resource) {
        return $resource('api/employeeLoanRequisitionForms/loanRequisitionByApproveStatus/:approveStatus', {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data != null){
                        console.log(data);
                    }
                    data = angular.fromJson(data);
                    return data;
                }
            }
        });
    })
    .factory('CheckEmployeeEligibleForLoanApplication', function ($resource) {
        return $resource('api/employeeLoanRequisitionForms/checkEmployeeEligibleForLoanApplication/:employeeInfoID', {}, {
            'query': {method: 'GET', isArray: true}
        });
    });




