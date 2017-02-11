'use strict';

angular.module('stepApp')
    .factory('VclRequisition', function ($resource, DateUtils) {
        return $resource('api/vclRequisitions/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.expectedDate = DateUtils.convertLocaleDateFromServer(data.expectedDate);
                    data.returnDate = DateUtils.convertLocaleDateFromServer(data.returnDate);
                    data.expectedArrivalDate = DateUtils.convertLocaleDateFromServer(data.expectedArrivalDate);
                    data.actionDate = DateUtils.convertLocaleDateFromServer(data.actionDate);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.expectedDate = DateUtils.convertLocaleDateToServer(data.expectedDate);
                    data.returnDate = DateUtils.convertLocaleDateToServer(data.returnDate);
                    data.expectedArrivalDate = DateUtils.convertLocaleDateToServer(data.expectedArrivalDate);
                    data.actionDate = DateUtils.convertLocaleDateToServer(data.actionDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.expectedDate = DateUtils.convertLocaleDateToServer(data.expectedDate);
                    data.returnDate = DateUtils.convertLocaleDateToServer(data.returnDate);
                    data.expectedArrivalDate = DateUtils.convertLocaleDateToServer(data.expectedArrivalDate);
                    data.actionDate = DateUtils.convertLocaleDateToServer(data.actionDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    });
