'use strict';

angular.module('stepApp')
    .factory('PgmsPenGrCalculation', function ($resource, DateUtils) {
        return $resource('api/pgmsPenGrCalculations/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    }).factory('PgmsPenGrVersionId', function ($resource) {
                return $resource('api/pgmsPenGrCalculationsVersionId/:penGrVersion', {},
                    {
                        'get': { method: 'GET'}
                    });
      }).factory('PgmsPenGrPrcRate', function ($resource) {
                return $resource('api/pgmsPenGrCalculationsRate/:pVersion/:rVersion/:wrkYear', {},
                    {
                        'get': { method: 'GET', isArray: true}
                    });
      });
