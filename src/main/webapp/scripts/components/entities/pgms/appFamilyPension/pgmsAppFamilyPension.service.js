'use strict';

angular.module('stepApp')
    .factory('PgmsAppFamilyPension', function ($resource, DateUtils) {
        return $resource('api/pgmsAppFamilyPensions/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.nomineDob = DateUtils.convertLocaleDateFromServer(data.nomineDob);
                    data.ApplyDate = DateUtils.convertLocaleDateFromServer(data.ApplyDate);
                    data.aprvDate = DateUtils.convertLocaleDateFromServer(data.aprvDate);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.nomineDob = DateUtils.convertLocaleDateToServer(data.nomineDob);
                    data.ApplyDate = DateUtils.convertLocaleDateToServer(data.ApplyDate);
                    data.aprvDate = DateUtils.convertLocaleDateToServer(data.aprvDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.nomineDob = DateUtils.convertLocaleDateToServer(data.nomineDob);
                    data.ApplyDate = DateUtils.convertLocaleDateToServer(data.ApplyDate);
                    data.aprvDate = DateUtils.convertLocaleDateToServer(data.aprvDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    }).factory('PgmsAppFamilyAttachByTypeAndPen', function ($resource) {
        return $resource('api/pgmsAppFamilyAttachsByTypeAndPen/:attacheType/:familyPensionId', {},
        {
            'get': { method: 'GET', isArray: true }
        });
    }).factory('PgmsFmailyPenEmpInfo', function ($resource) {
        return $resource('api/pgmsAppFamilyPenEmpInfo/:employeeId/:nid', {},
        {
           'get': { method: 'GET' }
        });
    }).factory('PgmsAppFamilyPending', function ($resource) {
    return $resource('api/pgmsAppFamilyPemdings/:statusType', {},
        {
            'get': { method: 'GET', isArray: true }
        });
    }).factory('PgmsAppFamilyAttachByPenId', function ($resource) {
          return $resource('api/pgmsAppFamilyAttachsByPenId/:familyPensionId', {},
          {
              'get': { method: 'GET', isArray: true }
          });
    });
