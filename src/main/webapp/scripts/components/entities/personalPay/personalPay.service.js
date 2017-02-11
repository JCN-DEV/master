'use strict';

angular.module('stepApp')
    .factory('PersonalPay', function ($resource, DateUtils) {
        return $resource('api/personalPays/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.effectiveDate = DateUtils.convertLocaleDateFromServer(data.effectiveDate);
                    data.dateCreated = DateUtils.convertLocaleDateFromServer(data.dateCreated);
                    data.dateModified = DateUtils.convertLocaleDateFromServer(data.dateModified);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.effectiveDate = DateUtils.convertLocaleDateToServer(data.effectiveDate);
                    data.dateCreated = DateUtils.convertLocaleDateToServer(data.dateCreated);
                    data.dateModified = DateUtils.convertLocaleDateToServer(data.dateModified);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.effectiveDate = DateUtils.convertLocaleDateToServer(data.effectiveDate);
                    data.dateCreated = DateUtils.convertLocaleDateToServer(data.dateCreated);
                    data.dateModified = DateUtils.convertLocaleDateToServer(data.dateModified);
                    return angular.toJson(data);
                }
            }
        });
    });
