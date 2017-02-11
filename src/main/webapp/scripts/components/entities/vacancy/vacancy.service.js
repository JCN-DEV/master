'use strict';

angular.module('stepApp')
    .factory('Vacancy', function ($resource, DateUtils) {
        return $resource('api/vacancys/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.promotionGBResolutionDate = DateUtils.convertLocaleDateFromServer(data.promotionGBResolutionDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.promotionGBResolutionDate = DateUtils.convertLocaleDateToServer(data.promotionGBResolutionDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.promotionGBResolutionDate = DateUtils.convertLocaleDateToServer(data.promotionGBResolutionDate);
                    return angular.toJson(data);
                }
            }
        });
    });