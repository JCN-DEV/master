'use strict';

angular.module('stepApp')
    .factory('PgmsElpc', function ($resource, DateUtils) {
        return $resource('api/pgmsElpcs/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.dateOfBirth = DateUtils.convertLocaleDateFromServer(data.dateOfBirth);
                    data.joinDate = DateUtils.convertLocaleDateFromServer(data.joinDate);
                    data.beginDateOfRetirement = DateUtils.convertLocaleDateFromServer(data.beginDateOfRetirement);
                    data.retirementDate = DateUtils.convertLocaleDateFromServer(data.retirementDate);
                    data.accDate = DateUtils.convertLocaleDateFromServer(data.accDate);
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
                    data.dateOfBirth = DateUtils.convertLocaleDateToServer(data.dateOfBirth);
                    data.joinDate = DateUtils.convertLocaleDateToServer(data.joinDate);
                    data.beginDateOfRetirement = DateUtils.convertLocaleDateToServer(data.beginDateOfRetirement);
                    data.retirementDate = DateUtils.convertLocaleDateToServer(data.retirementDate);
                    data.accDate = DateUtils.convertLocaleDateToServer(data.accDate);
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
                    data.dateOfBirth = DateUtils.convertLocaleDateToServer(data.dateOfBirth);
                    data.joinDate = DateUtils.convertLocaleDateToServer(data.joinDate);
                    data.beginDateOfRetirement = DateUtils.convertLocaleDateToServer(data.beginDateOfRetirement);
                    data.retirementDate = DateUtils.convertLocaleDateToServer(data.retirementDate);
                    data.accDate = DateUtils.convertLocaleDateToServer(data.accDate);
                    data.appDate = DateUtils.convertLocaleDateToServer(data.appDate);
                    data.aprvDate = DateUtils.convertLocaleDateToServer(data.aprvDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    }).factory('PgmsElpcBankInfo', function ($resource) {
                return $resource('api/pgmsElpcBankInfo/:empInfoId', {},
                    {
                        'get': { method: 'GET'}
                    });
      });
