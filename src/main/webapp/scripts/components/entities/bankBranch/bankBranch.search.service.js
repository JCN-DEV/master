'use strict';

angular.module('stepApp')
    .factory('BankBranchSearch', function ($resource) {
        return $resource('api/_search/bankBranchs/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('BankBranchsByUpazilaAndBank', function ($resource) {
        return $resource('api/bankBranchs/upazila/:upazilaId/bankSetup/:bankSetupId', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('BankBranchsOfADistrictByBank', function ($resource) {
        return $resource('api/bankBranchs/district/:districtId/bankSetup/:bankSetupId', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
