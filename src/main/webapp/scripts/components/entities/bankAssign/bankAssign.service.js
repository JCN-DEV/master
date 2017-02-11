'use strict';

angular.module('stepApp')
    .factory('BankAssign', function ($resource, DateUtils) {
        return $resource('api/bankAssigns/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.createdDate = DateUtils.convertLocaleDateFromServer(data.createdDate);
                    data.modifiedDate = DateUtils.convertLocaleDateFromServer(data.modifiedDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.createdDate = DateUtils.convertLocaleDateToServer(data.createdDate);
                    data.modifiedDate = DateUtils.convertLocaleDateToServer(data.modifiedDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.createdDate = DateUtils.convertLocaleDateToServer(data.createdDate);
                    data.modifiedDate = DateUtils.convertLocaleDateToServer(data.modifiedDate);
                    return angular.toJson(data);
                }
            }
        });
    });
