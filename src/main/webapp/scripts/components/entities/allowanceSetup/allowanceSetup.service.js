'use strict';

angular.module('stepApp')
    .factory('AllowanceSetup', function ($resource, DateUtils) {
        return $resource('api/allowanceSetups/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);
                    data.effectiveDate = DateUtils.convertLocaleDateFromServer(data.effectiveDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    data.effectiveDate = DateUtils.convertLocaleDateToServer(data.effectiveDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    data.effectiveDate = DateUtils.convertLocaleDateToServer(data.effectiveDate);
                    return angular.toJson(data);
                }
            }
        });
    });
