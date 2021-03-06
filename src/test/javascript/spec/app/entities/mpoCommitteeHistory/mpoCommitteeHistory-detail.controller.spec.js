'use strict';

describe('MpoCommitteeHistory Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockMpoCommitteeHistory, MockMpoCommitteePersonInfo;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockMpoCommitteeHistory = jasmine.createSpy('MockMpoCommitteeHistory');
        MockMpoCommitteePersonInfo = jasmine.createSpy('MockMpoCommitteePersonInfo');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'MpoCommitteeHistory': MockMpoCommitteeHistory,
            'MpoCommitteePersonInfo': MockMpoCommitteePersonInfo
        };
        createController = function() {
            $injector.get('$controller')("MpoCommitteeHistoryDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:mpoCommitteeHistoryUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
