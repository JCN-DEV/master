'use strict';

angular.module('stepApp')
    .factory('ProfessionalQualification', function ($resource, DateUtils) {
        return $resource('api/professionalQualifications/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.dateFrom = DateUtils.convertLocaleDateFromServer(data.dateFrom);
                    data.dateTo = DateUtils.convertLocaleDateFromServer(data.dateTo);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.dateFrom = DateUtils.convertLocaleDateToServer(data.dateFrom);
                    data.dateTo = DateUtils.convertLocaleDateToServer(data.dateTo);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.dateFrom = DateUtils.convertLocaleDateToServer(data.dateFrom);
                    data.dateTo = DateUtils.convertLocaleDateToServer(data.dateTo);
                    return angular.toJson(data);
                }
            }
        });
    });
