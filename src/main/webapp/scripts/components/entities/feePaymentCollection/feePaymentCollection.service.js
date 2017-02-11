'use strict';

angular.module('stepApp')
    .factory('FeePaymentCollection', function ($resource, DateUtils) {
        return $resource('api/feePaymentCollections/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.paymentDate = DateUtils.convertLocaleDateFromServer(data.paymentDate);
                    data.updatedTime = DateUtils.convertLocaleDateFromServer(data.updatedTime);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.paymentDate = DateUtils.convertLocaleDateToServer(data.paymentDate);
                    data.updatedTime = DateUtils.convertLocaleDateToServer(data.updatedTime);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.paymentDate = DateUtils.convertLocaleDateToServer(data.paymentDate);
                    data.updatedTime = DateUtils.convertLocaleDateToServer(data.updatedTime);
                    return angular.toJson(data);
                }
            }
        });
    });
