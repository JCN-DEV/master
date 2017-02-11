'use strict';

angular.module('stepApp')
    .factory('DearnessAssign', function ($resource, DateUtils) {
        return $resource('api/dearnessAssigns/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.effectiveDate = DateUtils.convertLocaleDateFromServer(data.effectiveDate);
                    data.stopDate = DateUtils.convertLocaleDateFromServer(data.stopDate);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.effectiveDate = DateUtils.convertLocaleDateToServer(data.effectiveDate);
                    data.stopDate = DateUtils.convertLocaleDateToServer(data.stopDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.effectiveDate = DateUtils.convertLocaleDateToServer(data.effectiveDate);
                    data.stopDate = DateUtils.convertLocaleDateToServer(data.stopDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    });
