'use strict';

angular.module('stepApp')
    .factory('PgmsAppRetirmntPen', function ($resource, DateUtils) {
        return $resource('api/pgmsAppRetirmntPens/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.appDate = DateUtils.convertLocaleDateFromServer(data.appDate);
                    data.aprvDate = DateUtils.convertLocaleDateFromServer(data.aprvDate);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.appDate = DateUtils.convertLocaleDateToServer(data.appDate);
                    data.aprvDate = DateUtils.convertLocaleDateToServer(data.aprvDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.appDate = DateUtils.convertLocaleDateToServer(data.appDate);
                    data.aprvDate = DateUtils.convertLocaleDateToServer(data.aprvDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    }).factory('HrNomineeInfosByEmpId', function ($resource) {
        return $resource('api/HrRetirmntNminesInfos/:empId', {},
        {
            'get': { method: 'GET', isArray: true}
        });
    }).factory('RetirementNomineeInfosByPenId', function ($resource) {
          return $resource('api/RetirementNomineesInfos/:penId', {},
          {
              'get': { method: 'GET', isArray: true}
          });
    }).factory('DeleteRetirementNomineeInfosByPenId', function ($resource) {
          return $resource('api/deleteRetirementNomineesInfos/:penId', {},
          {
              'get': { method: 'GET'}
          });
    }).factory('PgmsAppRetirementAttachByTypeAndPen', function ($resource) {
          return $resource('api/pgmsAppRetirmntPensByTypeAndPen/:attacheType/:retirementPenId', {},
          {
              'get': { method: 'GET', isArray: true}
          });
    }).factory('DeleteRetirementAttachInfosByPenId', function ($resource) {
          return $resource('api/deleteRetirementAttachInfos/:penId', {},
          {
              'get': { method: 'GET'}
          });
    }).factory('PgmsAppRetirementPending', function ($resource) {
          return $resource('api/pgmsAppRetirementPemdings/:statusType', {},
          {
              'get': { method: 'GET', isArray: true }
          });
    }).factory('PgmsAppRetirementAttachByPenId', function ($resource) {
        return $resource('api/pgmsAppRetirmntPensByPenId/:retirementPenId', {},
        {
            'get': { method: 'GET', isArray: true}
        });
    }).factory('BankInfosByEmpId', function ($resource) {
        return $resource('api/bankInfoByEmpId/:empId', {},
        {
            'get': { method: 'GET' }
        });
    });
