'use strict';

angular.module('stepApp')
    .factory('InstComiteFormation', function ($resource, DateUtils) {
        return $resource('api/instComiteFormations/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.formationDate = DateUtils.convertLocaleDateFromServer(data.formationDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.formationDate = DateUtils.convertLocaleDateToServer(data.formationDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.formationDate = DateUtils.convertLocaleDateToServer(data.formationDate);
                    return angular.toJson(data);
                }
            }
        });
    }).factory('InstComiteFormationWith', function ($resource, DateUtils) {
        return $resource('api/instComiteFormations/instMemShip/:id', {}, {
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    return angular.toJson(data);
                }
            }
        });
    }).factory('InstComiteFormationWithCommittee', function ($resource, DateUtils) {
        return $resource('api/instComiteFormations/withMembsers/:id', {}, {
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.formationDate = DateUtils.convertLocaleDateFromServer(data.formationDate);
                    return data;
                }
            }
        });
    });
