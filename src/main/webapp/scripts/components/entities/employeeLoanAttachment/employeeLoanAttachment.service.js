'use strict';

angular.module('stepApp')
    .factory('EmployeeLoanAttachment', function ($resource, DateUtils) {
        return $resource('api/employeeLoanAttachments/:id', {}, {
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
    }).factory('LoanAttachmentByEmployeeAndAppName', function ($resource) {
            return $resource('api/employeeLoanAttachments/employee/:id/:applicationName', {}, {
               'query': { method: 'GET', isArray: true}
            });
    }).factory('LoanAttachmentByEmployeeAndAppNameAndRequisitionId', function ($resource) {
            return $resource('api/employeeLoanAttachments/loanAttachment/:employeeInfoId/:applicationName/:requisitionId', {}, {
                'query': { method: 'GET', isArray: true}
            });
    });
