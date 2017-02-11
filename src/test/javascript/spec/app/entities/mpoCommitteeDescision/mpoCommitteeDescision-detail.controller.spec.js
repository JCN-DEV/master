'use strict';

describe('MpoCommitteeDescision Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockMpoCommitteeDescision, MockMpoCommitteePersonInfo, MockMpoApplication;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockMpoCommitteeDescision = jasmine.createSpy('MockMpoCommitteeDescision');
        MockMpoCommitteePersonInfo = jasmine.createSpy('MockMpoCommitteePersonInfo');
        MockMpoApplication = jasmine.createSpy('MockMpoApplication');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'MpoCommitteeDescision': MockMpoCommitteeDescision,
            'MpoCommitteePersonInfo': MockMpoCommitteePersonInfo,
            'MpoApplication': MockMpoApplication
        };
        createController = function() {
            $injector.get('$controller')("MpoCommitteeDescisionDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:mpoCommitteeDescisionUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
