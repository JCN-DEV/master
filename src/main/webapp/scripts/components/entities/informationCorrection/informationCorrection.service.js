'use strict';

angular.module('stepApp')
    .factory('InformationCorrection', function ($resource, DateUtils) {
        return $resource('api/informationCorrections/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.dob = DateUtils.convertLocaleDateFromServer(data.dob);
                    data.createdDate = DateUtils.convertLocaleDateFromServer(data.createdDate);
                    data.modifiedDate = DateUtils.convertLocaleDateFromServer(data.modifiedDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.dob = DateUtils.convertLocaleDateToServer(data.dob);
                    data.createdDate = DateUtils.convertLocaleDateToServer(data.createdDate);
                    data.modifiedDate = DateUtils.convertLocaleDateToServer(data.modifiedDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.dob = DateUtils.convertLocaleDateToServer(data.dob);
                    data.createdDate = DateUtils.convertLocaleDateToServer(data.createdDate);
                    data.modifiedDate = DateUtils.convertLocaleDateToServer(data.modifiedDate);
                    return angular.toJson(data);
                }
            }
        });
    });
