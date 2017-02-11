'use strict';

describe('InformationCorrectionStatusLog Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockInformationCorrectionStatusLog, MockInformationCorrection;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockInformationCorrectionStatusLog = jasmine.createSpy('MockInformationCorrectionStatusLog');
        MockInformationCorrection = jasmine.createSpy('MockInformationCorrection');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'InformationCorrectionStatusLog': MockInformationCorrectionStatusLog,
            'InformationCorrection': MockInformationCorrection
        };
        createController = function() {
            $injector.get('$controller')("InformationCorrectionStatusLogDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:informationCorrectionStatusLogUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
