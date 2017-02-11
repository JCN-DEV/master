'use strict';

angular.module('stepApp')
    .factory('MpoCommitteeDescisionSearch', function ($resource) {
        return $resource('api/_search/mpoCommitteeDescisions/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }) .factory('MpoCommitteeDescisionCurWithMpo', function ($resource) {
        return $resource('api/mpoCommitteeDescisions/current/:mpoApplicationid', {}, {
            'get': { method: 'GET', isArray: false}
        });
    }).factory('MpoCommitteeDescisionByMpo', function ($resource) {
        return $resource('api/mpoCommitteeDescisions/mpoApplication/:mpoApplicationid', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('MpoCommitteeDescisionByLogin', function ($resource) {
        return $resource('api/mpoCommitteeDescisions/currentLogin', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('MpoCommitteePendingApplicationByLogin', function ($resource) {
        return $resource('api/mpoCommitteeDescisions/pendingMpoApplications/currentLogin', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
