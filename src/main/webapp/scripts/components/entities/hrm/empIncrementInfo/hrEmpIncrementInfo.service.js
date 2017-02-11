'use strict';

angular.module('stepApp')
    .factory('HrEmpIncrementInfo', function ($resource, DateUtils) {
        return $resource('api/hrEmpIncrementInfos/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.incrementDate = DateUtils.convertLocaleDateFromServer(data.incrementDate);
                    data.basicDate = DateUtils.convertLocaleDateFromServer(data.basicDate);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.incrementDate = DateUtils.convertLocaleDateToServer(data.incrementDate);
                    data.basicDate = DateUtils.convertLocaleDateToServer(data.basicDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.incrementDate = DateUtils.convertLocaleDateToServer(data.incrementDate);
                    data.basicDate = DateUtils.convertLocaleDateToServer(data.basicDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    });
