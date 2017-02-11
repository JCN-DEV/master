'use strict';

angular.module('stepApp')
    .factory('MpoVacancyRoleTrade', function ($resource, DateUtils) {
        return $resource('api/mpoVacancyRoleTrades/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.crateDate = DateUtils.convertLocaleDateFromServer(data.crateDate);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.crateDate = DateUtils.convertLocaleDateToServer(data.crateDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.crateDate = DateUtils.convertLocaleDateToServer(data.crateDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    });
