'use strict';

describe('InstituteFinancialInfo Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockInstituteFinancialInfo;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockInstituteFinancialInfo = jasmine.createSpy('MockInstituteFinancialInfo');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'InstituteFinancialInfo': MockInstituteFinancialInfo
        };
        createController = function() {
            $injector.get('$controller')("InstituteFinancialInfoDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:instituteFinancialInfoUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
