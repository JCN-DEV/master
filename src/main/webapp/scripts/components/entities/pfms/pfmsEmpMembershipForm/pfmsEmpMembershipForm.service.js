'use strict';

angular.module('stepApp')
    .factory('PfmsEmpMembershipForm', function ($resource, DateUtils) {
        return $resource('api/pfmsEmpMembershipForms/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.applicationDate = DateUtils.convertLocaleDateFromServer(data.applicationDate);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.applicationDate = DateUtils.convertLocaleDateToServer(data.applicationDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.applicationDate = DateUtils.convertLocaleDateToServer(data.applicationDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    }).factory('PfmsEmpMembershipFormListByEmployee', function ($resource)
    {
        return $resource('api/pfmsEmpMembershipFormListByEmployee/:employeeInfoId', {},
            {
                'get': { method: 'GET', isArray: true}
            });
    });
