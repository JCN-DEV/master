'use strict';

angular.module('stepApp')
    .factory('Institute', function ($resource, DateUtils) {
        return $resource('api/institutes/:id', {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.dateOfEstablishment = DateUtils.convertLocaleDateFromServer(data.dateOfEstablishment);
                    data.firstApprovedEducationalYear = DateUtils.convertLocaleDateFromServer(data.firstApprovedEducationalYear);
                    data.lastApprovedEducationalYear = DateUtils.convertLocaleDateFromServer(data.lastApprovedEducationalYear);
                    data.firstMpoIncludeDate = DateUtils.convertLocaleDateFromServer(data.firstMpoIncludeDate);
                    data.lastMpoExpireDate = DateUtils.convertLocaleDateFromServer(data.lastMpoExpireDate);
                    data.nameOfTradeSubject = DateUtils.convertLocaleDateFromServer(data.nameOfTradeSubject);
                    data.lastApprovedSignatureDate = DateUtils.convertLocaleDateFromServer(data.lastApprovedSignatureDate);
                    data.lastCommitteeApprovedDate = DateUtils.convertLocaleDateFromServer(data.lastCommitteeApprovedDate);
                    data.lastCommitteeExpDate = DateUtils.convertLocaleDateFromServer(data.lastCommitteeExpDate);
                    data.lastCommitteeExpireDate = DateUtils.convertLocaleDateFromServer(data.lastCommitteeExpireDate);
                    data.lastMpoMemorialDate = DateUtils.convertLocaleDateFromServer(data.lastMpoMemorialDate);
                    data.lastMpoIncludeExpireDate = DateUtils.convertLocaleDateFromServer(data.lastMpoIncludeExpireDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT'
            }

        });
    }).factory('InstituteByCategory', function ($resource)
    {
        return $resource('api/institutesByCat/:cat', {},
            {
                'get': { method: 'GET', isArray: true}
            });
    });
