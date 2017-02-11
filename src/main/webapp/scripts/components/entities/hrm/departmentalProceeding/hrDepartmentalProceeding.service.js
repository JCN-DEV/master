'use strict';

angular.module('stepApp')
    .factory('HrDepartmentalProceeding', function ($resource, DateUtils) {
        return $resource('api/hrDepartmentalProceedings/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.formDate = DateUtils.convertLocaleDateFromServer(data.formDate);
                    data.toDate = DateUtils.convertLocaleDateFromServer(data.toDate);
                    data.goDate = DateUtils.convertLocaleDateFromServer(data.goDate);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.formDate = DateUtils.convertLocaleDateToServer(data.formDate);
                    data.toDate = DateUtils.convertLocaleDateToServer(data.toDate);
                    data.goDate = DateUtils.convertLocaleDateToServer(data.goDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.formDate = DateUtils.convertLocaleDateToServer(data.formDate);
                    data.toDate = DateUtils.convertLocaleDateToServer(data.toDate);
                    data.goDate = DateUtils.convertLocaleDateToServer(data.goDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    });
